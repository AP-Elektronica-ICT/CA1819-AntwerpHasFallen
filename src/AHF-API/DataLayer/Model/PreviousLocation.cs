using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class PreviousLocation
    {
        public int Id { get; set; }
        public int TeamId { get; set; }
        public Team Team { get; set; }
        public int LocationId { get; set; }
        public Location Location { get; set; }
    }
}
