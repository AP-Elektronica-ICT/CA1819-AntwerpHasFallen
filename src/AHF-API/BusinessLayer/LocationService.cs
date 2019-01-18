using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using System.Linq;
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
            return context.Locations.Include(q => q.Quiz).Include(s => s.subs).ToList();
        }

        public Location GetLocation(int id)
        {
            return context.Locations.SingleOrDefault(g => g.Id == id);
        }

        public List<Quizpuzzles> GetQuizByName(string name)
        {
            Location location = context.Locations.Include(q => q.Quiz).SingleOrDefault(g => g.Name == name);


            return location.Quiz.ToList();            
         }

        public List<SubstitionPuzzles> GetSubByName(string name)
        {
            Location location = context.Locations.Include(s => s.subs).SingleOrDefault(g => g.Name == name);

            return location.subs.ToList();
        }

        public List<DAD> GetDadByName(string name)
        {
            Location location = context.Locations.Include(d => d.dads).SingleOrDefault(g => g.Name == name);
            return location.dads.ToList();
        }

        public List<Anagram> GetAnagramByName(string name)
        {
            Location location = context.Locations.Include(a => a.anagrams).SingleOrDefault(g => g.Name == name);
            return location.anagrams.ToList();
        }
    }
}
