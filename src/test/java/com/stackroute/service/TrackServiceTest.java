package com.stackroute.service;

import com.stackroute.Service.TrackServiceImpl;
import com.stackroute.domain.Track;
import com.stackroute.exception.TrackAlreadyExistsException;
import com.stackroute.exception.TrackNotFoundException;
import com.stackroute.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TrackServiceTest {
    private Track track;
    private Optional optional;
    //Create a mock for UserRepository
    @Mock
    private TrackRepository trackRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    private TrackServiceImpl trackService;
    List<Track> list= null;


    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setTrackId(101);
        track.setTrackName("Jenny");
        track.setTrackComments("abc");
        list = new ArrayList<>();
        list.add(track);
    }

    @Test
    public void saveTrackTestSuccess() throws TrackAlreadyExistsException {

        when(trackRepository.save((Track)any())).thenReturn(track);
        Track savedUser = trackService.saveTrack(track);
        Assert.assertEquals(track,savedUser);
        //verify here verifies that userRepository save method is only called once
        verify(trackRepository,times(1)).save(track);

    }

    @Test(expected = TrackAlreadyExistsException.class)
    public void saveTrackTestFailure() throws TrackAlreadyExistsException {
        when(trackRepository.save((Track)any())).thenReturn(null);
        Track savedTrack = trackService.saveTrack(track);
        System.out.println("savedUser" + savedTrack);
        Assert.assertEquals(track,savedTrack);
       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/
    }

    @Test
    public void getAllTracks(){

        trackRepository.save(track);
        //stubbing the mock to return specific data
        when(trackRepository.findAll()).thenReturn(list);
       List<Track> trackList = trackService.getAllTracks();
      Assert.assertEquals(list,trackList);
    }

    @Test
    public void deleteTrack(){
        Track track=new Track(14,"hgdsf","udsgfds");
       trackRepository.delete(track);
        //when(trackRepository.delete((track)any())).thenReturn()
        boolean deletedTrack=trackRepository.existsById(14);
        assertEquals(false,deletedTrack);
    }

    @Test
    public void testUpdateTrackComments() throws TrackNotFoundException{

       //Track track=new Track(17,"gdf","abc");
       /* System.out.println(track);*/
        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        track.setTrackComments("hgfg");
        Track track1=trackService.updateComment(track);
        when(trackRepository.save((Track)any())).thenReturn(track1);
        Assert.assertEquals("hgfg",track1.getTrackComments());
    }
    @Test(expected = TrackNotFoundException.class)
    public void testUpdateTrackCommentsFailure() throws TrackNotFoundException{

        when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.empty());
        track.setTrackComments("better");
        Track track1=trackService.updateComment(track);
    }
    @Test
    public void testTrackByName() throws TrackNotFoundException{
        when(trackRepository.getTrackByName(track.getTrackName())).thenReturn(list);
        List<Track> list1=trackService.getAllTrackByName(track.getTrackName());
        assertEquals("Jenny",list1.get(0).getTrackName());
    }
    @Test(expected = TrackNotFoundException.class)
    public void testTrackByNameFailure() throws TrackNotFoundException{
        when(trackRepository.getTrackByName(track.getTrackName())).thenReturn(null);
        List<Track> list1=trackService.getAllTrackByName(track.getTrackName());
        assertEquals("Jenn",list1.get(0).getTrackName());;

    }


}
