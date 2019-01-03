using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BusinessLayer;
using Microsoft.AspNetCore.Mvc;

namespace AntwerpHasFallen.Controllers
{

    [Produces("application/json")]
    [Route("api/puzzles/substitution")]

    public class SubstitionPuzzleController : Controller
    {
        private readonly SubstitionService substitionService;

        public SubstitionPuzzleController(SubstitionService substitionService)
        {
            this.substitionService = substitionService;
        }

        [HttpGet]
        public IActionResult Allsubstitutionpuzzles()
        {
            return Ok(substitionService.GetAllsubs());
        }
        [Route("{id}")]
        [HttpGet]
        public IActionResult SubstitionPuzzle(int id)
        {
            SubstitionPuzzles puzzle = substitionService.GetQuestionById(id);
            if (puzzle != null)
                return Ok(substitionService.GetQuestionById(id));
            return NotFound();
        }
        
    }
}
