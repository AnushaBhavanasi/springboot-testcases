package com.stackroute.Service;

import com.stackroute.domain.Track;
import com.stackroute.exception.TrackAlreadyExistsException;
import com.stackroute.exception.TrackNotFoundException;
import com.stackroute.repository.TrackRepository;
import org.springframework.cache.annotation.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;

@Primary
@Service
@EnableCaching
@CacheConfig(
    cacheNames=("track")
        )


public class TrackServiceImpl implements  TrackService{
    TrackRepository trackRepository;
    public void simulateDelay()
    {
        try{
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @CacheEvict(allEntries = true)
    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        if(trackRepository.existsById(track.getTrackId())){
           throw new TrackAlreadyExistsException("this track id already exists");
        }
        Track savedTrack=trackRepository.save(track);
        if(savedTrack==null){
            throw new TrackAlreadyExistsException("track id already exists");
        }
        return savedTrack;
    }
    @Cacheable("track")
    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
    @CacheEvict(allEntries = true)
    @Override
    public boolean removeTrack(Track track) {
        //String str="not deleted";
          if(trackRepository.existsById(track.getTrackId())){
              trackRepository.deleteById(track.getTrackId());
              //str="deleted";
              return true;
          }
       return false;
    }
    @CacheEvict(allEntries = true)
    @Override
    public Track updateComment(Track track) throws TrackNotFoundException {
        System.out.println(track.getTrackId());
        Track track1=null;
        if (trackRepository.existsById(track.getTrackId())) {
            track1.setTrackComments(track.getTrackComments());
            trackRepository.save(track1);
            System.out.println(track1);
            return track1;
        } else {
            throw new TrackNotFoundException("track not found");
        }
    }

    @CacheEvict(allEntries = true)
    @Override
    public List<Track> getAllTrackByName(String trackName) {
        return (List<Track>) trackRepository.getTrackByName(trackName);
    }
}



