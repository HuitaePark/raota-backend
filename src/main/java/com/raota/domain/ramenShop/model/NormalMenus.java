package com.raota.domain.ramenShop.model;


import com.raota.domain.ramenShop.dto.NormalMenuDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class NormalMenus {

    @OneToMany(mappedBy = "ramenShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NormalMenu> values;

    public static NormalMenus init() {
        return new NormalMenus(new ArrayList<>());
    }

    public List<NormalMenuDto> getNormalMenusInfo(){
        return values.stream().map(NormalMenuDto::from).toList();
    }

    public void add(NormalMenu normalMenu){
        verifyMenuNameDuplicate(normalMenu.getName());
        values.add(normalMenu);
    }

    public Optional<NormalMenu> findMenuById(Long menuId){
        return values.stream()
                .filter(menu -> menu.getId().equals(menuId))
                .findFirst();
    }

    private void verifyMenuNameDuplicate(String name){
        if (values.stream().anyMatch(menu -> menu.getName().equals(name))) {
            throw new IllegalArgumentException("이미 존재하는 메뉴 이름입니다: " + name);
        }
    }
}
