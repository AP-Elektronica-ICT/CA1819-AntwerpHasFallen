using BusinessLayer;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AntwerpHasFallen.Controllers
{
	[Produces("application/json")]
	[Route("api/quiz")]

	public class QuizPuzzleController: Controller
    {
        private readonly QuizService quizService;

		public QuizPuzzleController(QuizService quizService)
        {
            this.quizService = quizService;
        }

        [HttpGet]
        public IActionResult getAllQuestions()
        {
            return Ok(quizService.GetQuestions());
        }

      
    }
}