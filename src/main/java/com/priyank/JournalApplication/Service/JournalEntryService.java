package com.priyank.JournalApplication.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priyank.JournalApplication.Entity.JournalEntry;
import com.priyank.JournalApplication.Entity.User;
import com.priyank.JournalApplication.Respository.JournalEntryRepository;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService; 

    public void saveJournalEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public void saveJournalEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(savedJournalEntry);
        userService.saveUser(user);
    }

    public List<JournalEntry> getAll(){
       return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        Optional<JournalEntry> foudEntry =  journalEntryRepository.findById(id);
        return foudEntry;
    }

    public boolean deleteByID(ObjectId id,String userName){
        User user = userService.findByUserName(userName);
        if(user.getJournalEntries().removeIf(x -> x.getId().equals(id))){
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
            return true;
        }
        return false;
        
    }
}
