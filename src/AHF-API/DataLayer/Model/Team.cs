using DataLayer.Model.InventoryModel;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Team
    {
        public Team(string name)
        {
            Name = name;
            Players = new List<Player>();
        }
        public Team()
        {
            Players = new List<Player>();
        }
        public int Id { get; set; }
        public string Name { get; set; }
        public List<Player> Players { get; set; }
        public Inventory Inventory { get; set; }
    }
}
