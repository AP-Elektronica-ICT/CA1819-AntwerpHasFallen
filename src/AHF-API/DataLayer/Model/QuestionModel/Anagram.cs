using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.QuestionModel
{
    public class Anagram : Question
    {
        
        public string Scrambled { get; set; }
        public string Sollution { get; set; }
        public string Tip { get; set; }
    }
}
