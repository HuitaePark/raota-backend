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
public class NormalMenus {

    @OneToMany(mappedBy = "ramenShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NormalMenu> values = new ArrayList<>();

    public void add(NormalMenu normalMenu){
        verifyMenuNameDuplicate(normalMenu.getName());
        values.add(normalMenu);
    }

    private void verifyMenuNameDuplicate(String name){
        if (values.stream().anyMatch(menu -> menu.getName().equals(name))) {
            throw new IllegalArgumentException("이미 존재하는 메뉴 이름입니다: " + name);
        }
    }
}
