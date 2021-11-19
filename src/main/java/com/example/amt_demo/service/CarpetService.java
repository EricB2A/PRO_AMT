package com.example.amt_demo.service;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarpetService {
    final private CarpetRepository carpetRepository;

    /**
     * Constructor of CarpetService
     * @param carpetRepository
     */
    public CarpetService(CarpetRepository carpetRepository) {
        this.carpetRepository = carpetRepository;
    }

    public Iterable<Carpet> findAll() {
        return carpetRepository.findAll();
    }

    public boolean existsById(int id) {
        return carpetRepository.existsById(id);
    }

    public void deleteById(int id) {
        carpetRepository.deleteById(id);
    }

    public Carpet findByName(String name) {
        return carpetRepository.findByName(name);
    }

    public void save(Carpet newCarpet) {
        carpetRepository.save(newCarpet);
    }

    public Optional<Carpet> findById(int id) {
        return carpetRepository.findById(id);
    }

    public Carpet findId(Integer carpetId) {
        return carpetRepository.findId(carpetId);
    }

    public void delete(Carpet carpet) {
        carpetRepository.delete(carpet);
    }

    public Iterable<Carpet> findErrorDeletion(String id) {
        return carpetRepository.findErrorDeletion(Integer.valueOf(id));
    }

    public long count() {
        return carpetRepository.count();
    }
}