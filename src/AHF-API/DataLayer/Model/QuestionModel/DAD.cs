using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.QuestionModel
{
    public class DAD : Question
    {
        public String Answers { get; set; }
        public String Question { get; set; }

        public String CorrectOrder { get; set; }



    }
}
