using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class PreviousLocation
    {
        public int TeamId { get; set; }
        [JsonIgnore]
        public Team Team { get; set; }
        public int LocationId { get; set; }
        [JsonIgnore]
        public Location Location { get; set; }
    }
}
