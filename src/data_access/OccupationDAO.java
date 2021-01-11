package data_access;

import java.util.ArrayList;

public interface OccupationDAO {
    public abstract ArrayList<OccupationDTO> getAll();
    public abstract ArrayList<OccupationDTO> getRoomOccupation(int idRoom);
    public abstract void setOccupation(OccupationDTO occupation);
    public abstract void setOccupationList(ArrayList<OccupationDTO> occupation);

}
