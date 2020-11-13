package com.rihem.medicament.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rihem.medicament.entities.Medicament;
import com.rihem.medicament.service.MedicamentService;

@Controller
public class MedicamentController implements CommandLineRunner {
	
	@Autowired
	MedicamentService medicamentService;
	
	@Override
	public void run(String... args) throws Exception {
		
		
	}
	
	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createMedicament";
	}
	
	@RequestMapping("/saveMedicament")
		public String saveMedicament(@ModelAttribute("medicament") Medicament medicament,
				@RequestParam("date") String date,
				ModelMap modelMap) throws ParseException{
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateCreation = dateformat.parse(String.valueOf(date));
		medicament.setDateCreation(dateCreation);
		
		Medicament saveMedicament = medicamentService.saveMedicament(medicament);
		String msg = "medicament enregistré avec Id"+saveMedicament.getIdMedicament();
		modelMap.addAttribute("msg",msg);
		List<Medicament> drugs= medicamentService.getAllMedicaments();
		modelMap.addAttribute("medicaments",drugs);
		return "listeMedicaments";
	}
	
	@RequestMapping("/ListeMedicaments")
	public String listeMedicament(ModelMap modelMap,
			@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "2") int size) {
		Page<Medicament> drugs = medicamentService.getAllMedicamentsParPage(page, size);
		modelMap.addAttribute("medicaments", drugs);
		modelMap.addAttribute("pages", new int[drugs.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeMedicaments";
	}
	@RequestMapping("/supprimerMedicament")
	public String supprimerProduit(@RequestParam("id") Long id,
			ModelMap modelMap,
			@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "2") int size) {
		medicamentService.deleteMedicamentById(id);
		Page<Medicament> drugs= medicamentService.getAllMedicamentsParPage(page, size);
		modelMap.addAttribute("medicaments", drugs);
		modelMap.addAttribute("pages", new int[drugs.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		
		
		return "listeMedicaments";
	}
	
	@RequestMapping("/modifierMedicament")
	public String editerMedicament(@RequestParam("id") Long id,
			ModelMap modelMap) {
		Medicament m = medicamentService.getMedicament(id);
		modelMap.addAttribute("medicament", m);
		return "editerMedicament";
	}
	
	@RequestMapping("/updateMedicament")
	public String updateMedicament(@ModelAttribute("medicament") Medicament medicament,
			@RequestParam("date") String date, ModelMap modelMap) throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateCreation = dateformat.parse(String.valueOf(date));
		medicament.setDateCreation(dateCreation);
		
		medicamentService.updateMedicament(medicament);
		List<Medicament> drugs= medicamentService.getAllMedicaments();
		modelMap.addAttribute("medicaments",drugs);
		return "listeMedicaments";
	}

	
}
