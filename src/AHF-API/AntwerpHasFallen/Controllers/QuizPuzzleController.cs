using BusinessLayer;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DataLayer.Model;

namespace AntwerpHasFallen.Controllers
{
	[Produces("application/json")]
	[Route("api/quiz")]

	public class QuizPuzzleController: Controller
    {
        private readonly QuizService quizService;
        private readonly TeamService teamService;

		public QuizPuzzleController(QuizService quizService, TeamService teamService)
        {
            this.quizService = quizService;
            this.teamService = teamService;
        }

        [HttpGet]
        public IActionResult getAllQuestions()
        {
            return Ok(quizService.GetQuestions());
        }

        [Route("{teamid}/{status}")]
        [HttpGet]
        public IActionResult updategold(int teamid, bool status)
        {
            QuizService quiz = quizService;
            quiz.updatePrice(status, teamid);
            return Ok();
        }
  

      
    }
}