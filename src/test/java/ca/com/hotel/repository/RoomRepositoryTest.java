package ca.com.hotel.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ca.com.hotel.domain.RoomEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RoomRepository roomRepository;

	@Test
	public void testIdPersistence() {
		RoomEntity roomEntity = new RoomEntity();
		roomEntity = this.entityManager.persistAndFlush(roomEntity);
		assertTrue(this.roomRepository.findById(roomEntity.getId()).isPresent());
	}


}
