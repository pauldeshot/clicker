package ClickerBot.DTO;

import ClickerBot.DTO.CropsInfo;

import java.util.Map;

public class FarmData {
    public Map<String, CropsInfo> crops;
    public Map<String, Mineral> minerals;
    public Map<String, Integer> seeds;

    public Map<String, Building> buildings;

    public PotionHouse potionHouse;

}
