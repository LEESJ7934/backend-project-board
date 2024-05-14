import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Quiz1 {
    @Test
    public void junitTest(){
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";


        assertThat(name1).isEqualTo(name2);
        assertThat(name1).isNotEqualTo(name3);
    }
}
