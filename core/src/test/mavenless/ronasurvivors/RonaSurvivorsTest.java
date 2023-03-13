package mavenless.ronasurvivors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RonaSurvivorsTest {
    
    @Test
    public void firstTest() {
        RonaSurvivors rs = new RonaSurvivors();
        assertEquals(rs.batch, null);

        assertEquals(1, 1);
    }

}
