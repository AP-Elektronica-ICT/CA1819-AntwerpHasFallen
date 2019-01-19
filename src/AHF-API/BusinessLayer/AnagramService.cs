using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using DataLayer;
using DataLayer.Model;

namespace BusinessLayer
{
   public  class AnagramService
    {
        private readonly GameContext context;

        public AnagramService(GameContext context)
        {
            this.context = context;

        }
        public List<Anagram> GetQuestions()
        {
            return context.AnagramPuzzles.ToList();
        }

        
    }
}
