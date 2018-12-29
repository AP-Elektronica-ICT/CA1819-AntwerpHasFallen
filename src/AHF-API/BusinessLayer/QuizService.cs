using DataLayer;
using DataLayer.Model;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
   public class QuizService
    {
        private readonly GameContext context;
        
        public QuizService(GameContext context)
        {
            this.context = context;
        }

        public List<Quizpuzzles> GetQuestions()
        {
            return context.Quizpuzzles.ToList();
        }

      

    }
}
