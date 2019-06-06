package com.stackroute.repository;

import com.stackroute.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest

public class TrackRepositoryTest {
    @Autowired
    private TrackRepository trackRepository;
    private Track track;
    @Before
    public void setUp()
    {
        track= new Track();
        track.setTrackId(10);
        track.setTrackName("John");
        track.setTrackComments("dhgvs");
    }
    @After
    public void tearDown(){

       //trackRepository.deleteAll();
    }


    @Test
    public void testSaveTrack(){
     trackRepository.save(track);
    Track fetchTrack = trackRepository.findById(track.getTrackId()).get();
        System.out.println(fetchTrack);
        assertEquals(10,fetchTrack.getTrackId());

    }
    //test for insert operation
    @Test
    public void testSaveTrackFailure(){
        Track testUser = new Track();
        trackRepository.save(track);
        Track fetchUser = trackRepository.findById(track.getTrackId()).get();
        Assert.assertNotSame(testUser,track);
    }

    @Test
    public void testGetAllTracks() {
        Track track = new Track(13,"John","gfgd");
        Track track1 = new Track(25,"track1","dfd");
        trackRepository.save(track);
        trackRepository.save(track1);

        List<Track> list = (List<Track>)trackRepository.findAll();
        assertEquals("John", list.get(0).getTrackName());
    }
    //test for delete operation
    @Test
    public void testDeleteTrack(){
        Track track=new Track(14,"hgdsf","udsgfds");
        trackRepository.delete(track);
        boolean deletedTrack=trackRepository.existsById(14);
        assertEquals(false,deletedTrack);
    }
    @Test
    public void testDeleteTrackFailure(){
        Track testUser = new Track();
        trackRepository.delete(track);
        boolean trackDelete=trackRepository.existsById(14);
        Assert.assertNotSame(true,trackDelete);
    }
    //test for update operation
    @Test
    public void testUpdateTrack(){
        Track track=new Track(13,"undiporadhey sad version","Husharu");
        Track track1=new Track(14,"hdshvgdcs","vcshc");
        trackRepository.save(track);
        trackRepository.save(track1);
        trackRepository.findById(track.getTrackId()).get().setTrackComments("vcshc");
        System.out.println(track);
        List<Track> list=trackRepository.findAll();
        Assert.assertEquals("vcshc",list.get(0).getTrackComments());
    }

    @Test
    public void testUpdateTrackFailure(){
        Track track=new Track(3,"undiporadhe sad version","Husharu");
        Track track1=new Track(4,"gcfdxcc","gcfgv");
        trackRepository.save(track);
        trackRepository.save(track1);
        trackRepository.findById(track.getTrackId()).get().setTrackComments("gcfgv");
        List<Track> list=trackRepository.findAll();
        Assert.assertNotSame("Husharu",list.get(0).getTrackComments());
    }
}
