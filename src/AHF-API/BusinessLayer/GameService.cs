﻿using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace BusinessLayer
{
    public class GameService
    {
        private readonly GameContext context;

        public GameService(GameContext context)
        {
            this.context = context;
        }

        public List<Game> getGames()
        {
            return context.Games.Include(g => g.Teams).ToList();
        }
        
    }
}