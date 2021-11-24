package my.gun_level_db;


import lombok.var;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GunAggregation {

    interface Oper<T,R>{
        Map<String, Gun> bean(T oldObject, R newObject);
    }

    private static Map<OperType, Oper> map = new HashMap<OperType, Oper>(){{

        Oper<Gun, Gun> gunGun = (a, b) -> {
            return new HashMap<String, Gun>(){{
                put(a.getName(), a);
                put(b.getName(), b);
            }};
        };

        Oper<Gun, Bullet> gunBullet = (a, b) -> {
            if(a.getName() == b.getGunName()){
                List list = a.getBullets().stream().map(v -> v).collect(Collectors.toList());
                list.add(b);
                a.setBullets(list);
            }
            return new HashMap<String, Gun>(){{
                put(a.getName(), a);
            }};
        };

        Oper<Map<String,Gun>, Gun> mapGun = (a, b) -> {
            a.put(b.getName(), b.copy());
            return a;
        };

        Oper<Map<String, Gun>, Bullet> mapBullet = (a, b) -> {
            try {
                var gun = a.get(b.getGunName()).copy();
                gun.getBullets().add(b.copy());
                a.put(gun.getName(), gun);
            }
            catch (Exception e){
            }
            finally {
                return a;
            }
        };

        Oper<Bullet, Gun> bulletGun = (a, b) -> {
            return new HashMap<String, Gun>(){{
                put(b.getName(), b);
            }};
        };

        Oper<Bullet, Bullet> bulletBullet = (a, b) -> {
            return new HashMap<String, Gun>();
        };

        Oper<Object, Object> defaultOper = (a, b) -> {
            return (HashMap) a;
        };

        put(OperType.GUN_GUN, gunGun);
        put(OperType.GUN_BULLET, gunBullet);
        put(OperType.MAP_GUN, mapGun);
        put(OperType.MAP_BULLET, mapBullet);
        put(OperType.BULLET_GUN, bulletGun);
        put(OperType.BULLET_BULLET, bulletBullet);
        put(OperType.DEFAULT, defaultOper);
    }};

    public Map<String, Gun> bean(Object oldObject, Object newObject){
        return map.get(OperType.toType(oldObject, newObject)).bean(oldObject, newObject);
    }
}
