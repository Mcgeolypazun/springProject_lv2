package com.sparta.springauth;

import com.sparta.springauth.food.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest//빈 주입받어오는것 DI사용가능하다.
public class BeanTest {

    //@Autowired//자동 주입
    //Food food;//chicken , pizza 두개이상의 bean객체가 있어서 뭘 주입할지 모름 등록이 된 빈의 이름 직접 명시함
   // Food pizza;

    //@Autowired//빈타입으로 지원하는데 결정하지 못하면 이름으로 결정함
  //  Food chicken;

//    @Test
//    @DisplayName("테스트")
//    void test1(){
//        pizza.eat();
//        chicken.eat();
//    }//해당하는 타입을 빈이 주입해준다는 것을 확인할 수 있다.


    @Autowired
    @Qualifier("pizza")//qualifier된 빈객체 못찾음 단어를 다르게 하면
    Food food;

    @Test
    @DisplayName("Primary 와 Qualifier 우선순위 확인")
    void test1(){
        food.eat();
    }//chicken에 @Primary를 달아 chicken이 우선적으로 나오게

    //같은 타입의 빈이라도 qualify의 우선순위가 더 높다.
    // 하지만 주입받고자 하는곳에 넣어주어야 함 범용적으로는 primary를 지엽적으로는 Qualifier를 사용하는것이 좋음
    //좁은 범위가 우선순위가 더 높다. primary < Qualifier
}
