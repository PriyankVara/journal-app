package com.priyank.JournalApplication.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priyank.JournalApplication.Entity.JournalEntry;
import com.priyank.JournalApplication.Entity.User;
import com.priyank.JournalApplication.Service.JournalEntryService;
import com.priyank.JournalApplication.Service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> saveEntry(@RequestBody JournalEntry journalEntry){
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.saveJournalEntry(journalEntry, userName);
        return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
    } catch (Exception e) {
        System.err.println(e.getMessage());
        return new ResponseEntity<>(journalEntry,HttpStatus.BAD_REQUEST);
    }
   
    
}
@GetMapping()
public ResponseEntity<?> getAllJournalEntriesOfUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    User user = userService.findByUserName(userName);
    List<JournalEntry> journalEntries = user.getJournalEntries();
    return new ResponseEntity<>(journalEntries,HttpStatus.OK);
}

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(myId);
            if(journalEntry != null){
                return new ResponseEntity<>(journalEntry,HttpStatus.FOUND);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteResponseEntityById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if(journalEntryService.deleteByID(myId,userName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      
    }
    @PutMapping("id/{myId}")
    public ResponseEntity<?> putMethodName(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(myId).orElse(null);

        if(oldEntry != null){
            oldEntry.setDate(LocalDateTime.now());
            oldEntry.setContent( newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
            journalEntryService.saveJournalEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
