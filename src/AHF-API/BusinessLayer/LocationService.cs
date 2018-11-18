using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace BusinessLayer
{
    public class LocationService
    {
        private readonly GameContext context;

        public LocationService(GameContext context)
        {
            this.context = context;
        }

        public List<Location> getLocations()
        {
            return context.Locations.ToList();
        }

        public Location GetLocation(int id)
        {
            return context.Locations.SingleOrDefault(g => g.Id == id);
        }
    }
}
