using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Game
    {
        public int Id { get; set; }
        public List<Team> Teams { get; set; }
    }
}
