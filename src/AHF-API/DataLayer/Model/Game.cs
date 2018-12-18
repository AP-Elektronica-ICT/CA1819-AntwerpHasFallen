using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Game
    {
        public Game()
        {
            Teams = new List<Team>();
        }
        public int Id { get; set; }
        public List<Team> Teams { get; set; }
        public List<Location> Locations { get; set; }
    }
}
