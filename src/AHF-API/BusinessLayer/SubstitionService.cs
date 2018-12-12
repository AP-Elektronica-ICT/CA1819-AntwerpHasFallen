using DataLayer;
using System;
using System.Collections.Generic;
using System.Text;

namespace BusinessLayer
{
    public class SubstitionService
    {
        private readonly GameContext context;
        public SubstitionService(GameContext context)
        {
            this.context = context;

        }

        public SubstitionPuzzles GetQuestionById(int id)
        {
            return context.SubstitionPuzzles.Find(id);

        }
    }
}
