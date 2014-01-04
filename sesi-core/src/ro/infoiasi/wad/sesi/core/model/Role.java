package ro.infoiasi.wad.sesi.core.model;

import java.util.HashMap;
import java.util.Map;

public enum Role {

    STUDENT(1),
    COMPANY(2),
    TEACHER(3),
    ANONYMOUS(0);


    private final int id;

    Role(int id){
        this.id = id;
    }

    private static Map<Integer, Role> IDS = new HashMap<Integer, Role>(){
        {
            put(1, STUDENT);
            put(2, COMPANY);
            put(3, TEACHER);
            put(0, ANONYMOUS);
        }
    };

    public static Role fromId(int id) {
        return IDS.get(id);
    }
    public int getId() {
        return id;
    }

}
