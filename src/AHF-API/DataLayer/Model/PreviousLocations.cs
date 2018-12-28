using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class PreviousLocations
    {
        public int TeamId { get; set; }
        public Team Team { get; set; }
        public int LocationId { get; set; }
        public Location Location { get; set; }
    }
}
