using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{

    public class Quizpuzzles
    {
        public int ID { get; set; }

        public List<String> Answers { get; set; }
        public List<String> Questions { get; set; }
        public String CorrectAnswer { get; set; }
        
    }
}

   



