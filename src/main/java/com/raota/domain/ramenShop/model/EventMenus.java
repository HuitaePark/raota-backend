package com.raota.domain.ramenShop.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class EventMenus {

    @OneToMany(mappedBy = "ramenShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventMenu> values = new ArrayList<>();

    public void add(EventMenu eventMenu){
        verifyMenuNameDuplicate(eventMenu.getName());
        values.add(eventMenu);
    }

    private void verifyMenuNameDuplicate(String name){
        if (values.stream().anyMatch(menu -> menu.getName().equals(name))) {
            throw new IllegalArgumentException("이미 존재하는 메뉴 이름입니다: " + name);
        }
    }
}
