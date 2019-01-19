using DataLayer.Model.QuestionModel;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Location
    {
        public Location(double lat, double lon, string name, int time)
        {
            Lat = lat;
            Lon = lon;
            Name = name;
            Time = time;
            PreviousLocations = new List<PreviousLocation>();
        }

        public int Id { get; set; }
        public List<Quizpuzzles> Quiz { get; set; }
        public List<SubstitionPuzzles> subs { get; set; }
        public List<DAD> dads { get; set; }
        public List<Anagram> anagrams { get; set; }
        public double Lat { get; set; }
        public double Lon { get; set; }
        public string Name { get; set; }
        public int Time { get; set; }
        public List<PreviousLocation> PreviousLocations { get; set; }

    }
}
