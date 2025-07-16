import pika
import uuid
import json
from datetime import datetime

# Povezivanje na lokalni RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

# Kreiranje fanout exchange-a 
channel.exchange_declare(exchange='ad_exchange', exchange_type='fanout')

# Kreiraj privremeni, jedinstveni queue za ovu instancu agencije
result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue

# Veži queue na fanout exchange
channel.queue_bind(exchange='ad_exchange', queue=queue_name)

print(f"📡 Agencija ({queue_name}) sluša poruke za reklamiranje...")

# Callback kada se poruka primi
def callback(ch, method, properties, body):
    data = json.loads(body.decode())
    raw_time = data['timeOfPublishing']
    # Pretvaranje liste u datetime objekat
    dt = datetime(*raw_time)

    # Formatiranje datuma u čitljiv oblik
    formatted_time = dt.strftime("%d.%m.%Y. u %H:%M")
    
    print("📢 PRIMLJENA REKLAMA:")
    print("📝 Opis:", data['description'])
    print("📅 Datum:", formatted_time)
    print("👤 Korisnik:", data['username'])
    print("----")

channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)

# Čekaj poruke
channel.start_consuming()