﻿using DataLayer.Model.InventoryModel;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Team
    {
        public Team(string name, int money)
        {
            Name = name;
            Money = money;
            Players = new List<Player>();
            PreviousLocations = new List<PreviousLocation>();
        }
        public Team()
        {
            Players = new List<Player>();
            PreviousLocations = new List<PreviousLocation>();
        }
        public int Id { get; set; }
        public string Name { get; set; }
        public List<Player> Players { get; set; }
        public Inventory Inventory { get; set; }
        public int Money { get; set; }
        public int TimerOffset { get; set; }
        public string Blackout { get; set; }
        public List<PreviousLocation> PreviousLocations { get; set; }
    }
}
