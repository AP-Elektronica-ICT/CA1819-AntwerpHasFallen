using DataLayer;
using DataLayer.Model;
using DataLayer.Model.InventoryModel;
using DataLayer.Model.QuestionModel;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
    public class RewardService
    {
        private readonly GameContext context;
        private readonly InventoryService inventoryService;

        public RewardService(GameContext context)
        {
            this.context = context;
            inventoryService = new InventoryService(context);
        }

        public Team Reward(int teamId, string difficulty, bool answer, bool gotIngredient, List<string> missingIngredients)
        {
            Team team = context.Teams.Include(t => t.Inventory).ThenInclude(i => i.Ingredients).ThenInclude(i => i.Item).SingleOrDefault(t => t.Id == teamId);
            List<Item> missingIngredientsList = new List<Item>();
            Random r = new Random();

            foreach (string m in missingIngredients)
            {
                missingIngredientsList.Add(context.Items.SingleOrDefault(i => i.Name == m));
            }

            if (team != null)
            {
                if (answer)
                {
                    switch (difficulty.ToLower())
                    {
                        case "high":
                            inventoryService.addIngredient(team.Inventory.Id, missingIngredientsList[r.Next(missingIngredientsList.Count)].Id);
                            team.Money += r.Next(40, 50);
                            break;
                        case "medium":
                            if (!gotIngredient)
                            {
                                if (r.Next(1, 2) == 1)
                                    inventoryService.addIngredient(team.Inventory.Id, missingIngredientsList[r.Next(missingIngredientsList.Count)].Id);
                                else
                                    team.Money += r.Next(30, 40);
                            }
                            else
                                team.Money += r.Next(30, 40);
                            break;
                        case "low":
                            team.Money += r.Next(20, 30);
                            break;
                        default:
                            break;
                    }
                }
                else
                    team.Money -= 10;

                context.SaveChanges();
            }

            return team;
        }
    }
}
