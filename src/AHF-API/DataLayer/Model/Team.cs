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
        }
        public Team()
        {

        }
        public int Id { get; set; }
        public string Name { get; set; }
        public int PlayerCount { get; set; }
    }
}
