using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Anagram
    {
        public int Id { get; set;  }
        public string Scrambled { get; set; }
        public string Sollution { get; set; }
        public string Tip { get; set; }
    }
}
