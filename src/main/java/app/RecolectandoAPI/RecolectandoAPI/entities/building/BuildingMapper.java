package app.RecolectandoAPI.RecolectandoAPI.entities.building;

import app.RecolectandoAPI.RecolectandoAPI.entities.sector.Sector;
import org.springframework.stereotype.Service;

@Service
public class BuildingMapper {

    public BuildingResponse toBuildingResponse(Building building){
        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .address(building.getAddress())
                .sectors_names(building.getSectors().stream().map(Sector::getName).toList())
                .build();
    }

    public Building toBuilding(BuildingRequest buildingRequest){
        return Building.builder()
                .id(buildingRequest.getId())
                .name(buildingRequest.getName())
                .address(buildingRequest.getAddress())
                .build();
    }

    //El id o los sectores no deben modificarse!
    public void updateBuildingFromBuildingRequest(BuildingRequest bR, Building b) {
        b.setName(bR.getName());
        b.setAddress(bR.getAddress());
    }


}
