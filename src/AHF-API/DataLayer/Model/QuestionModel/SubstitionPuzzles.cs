using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.QuestionModel
{
    public class SubstitionPuzzles : Question
    {
        public string Key { get; set; }
        public string Solution { get; set; }
        public string ClearText { get; set; }
    }
}
