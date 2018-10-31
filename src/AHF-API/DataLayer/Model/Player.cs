using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Player
    {
        public int Id { get; set; }
        public Game Game { get; set; }
        public int TeamId { get; set; }
    }
}
