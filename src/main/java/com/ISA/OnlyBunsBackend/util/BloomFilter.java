package com.ISA.OnlyBunsBackend.util;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.List;
import java.util.zip.CRC32;

@Component
public class BloomFilter {
    private static final long DEFAULT_N = 1000; // Default expected number of elements
    private static final double DEFAULT_P = 0.01; // Default false positive probability

    private final int m, k; // k - number of hash functions, m - size of the array
    private final BitSet filter;

    // Constructor for creating a new Bloom filter
    public BloomFilter() {
        this.m = (int) Math.ceil((-DEFAULT_N * Math.log(DEFAULT_P)) / Math.pow(Math.log(2), 2));
        this.k = (int) Math.ceil((double) m / DEFAULT_N * Math.log(2));
        this.filter = new BitSet(m);
    }


    @Autowired
    private UserRepository userRepository;  // ili neki repo koji čita korisnike

    @PostConstruct
    public void init() {
        System.out.println("INIT METODA BLOOM FILTERA POZVANA");
        List<User> users = userRepository.findAll();
        System.out.println("Broj korisnika: " + users.size());
        for (User user : users) {
            this.add(user.getUsername());
        }
    }

    // Adding a new element to the Bloom filter
    public void add(String key) {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < k; i++) {
            int hashValue = hash(bytes, i) % m;
            filter.set(Math.abs(hashValue));
        }
    }

    // Checking if an element might be in the Bloom filter
    public boolean has(String key) {
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < k; i++) {
            int hashValue = hash(bytes, i) % m;
            if (!filter.get(Math.abs(hashValue))) {
                return false;
            }
        }
        return true;
    }

    // Simplified hash function using CRC32
    private int hash(byte[] data, int seed) {
        CRC32 crc = new CRC32();
        crc.update(data);
        crc.update(seed);
        return (int) crc.getValue();
    }
}