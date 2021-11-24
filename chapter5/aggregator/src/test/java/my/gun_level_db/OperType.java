package my.gun_level_db;

import java.util.Map;

public enum OperType {
    GUN_GUN,
    GUN_BULLET,
    MAP_GUN,
    MAP_BULLET,
    BULLET_GUN,
    BULLET_BULLET,
    DEFAULT;
    static public OperType toType(Object oldObect, Object newObject){
        if(oldObect instanceof Gun && newObject instanceof Gun){
            return OperType.GUN_GUN;
        }
        if(oldObect instanceof Gun && newObject instanceof Bullet){
            return OperType.GUN_BULLET;
        }
        if(oldObect instanceof Map && newObject instanceof Bullet){
            return OperType.MAP_BULLET;
        }
        if(oldObect instanceof Map && newObject instanceof Gun){
            return OperType.MAP_GUN;
        }
        if(oldObect instanceof Bullet && newObject instanceof Gun){
            return BULLET_GUN;
        }
        if(oldObect instanceof Bullet && newObject instanceof  Bullet){
            return BULLET_BULLET;
        }
        return DEFAULT;
    }
}
