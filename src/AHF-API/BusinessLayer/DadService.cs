using System;
using System.Collections.Generic;
using System.Text;
using DataLayer;
using DataLayer.Model;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer
{
    public class DadService
    {
        private readonly GameContext context;

        public DadService(GameContext context)
        {
            this.context = context;
        }
            
        public List<DAD> Getquestions()
        {
            return context.Dadpuzzles.ToList();
        }
        
        
    }
}
