/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.dyck.brandon.gedcomsite;

import static j2html.TagCreator.*;
import j2html.tags.DomContent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.model.Name;
import org.folg.gedcom.model.Person;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author brandon
 */
public class HTMLGenerator {
	public static void fromModel(Gedcom model)
			throws IllegalArgumentException, IOException {
		if (model == null) {
			throw new IllegalArgumentException();
		}
		String dirname = "gedsite_work";
	Files.createDirectories(Paths.get(dirname));
		for (Person p : model.getPeople()) {
			Path path = Paths.get(dirname, personToFilename(p));
			try (
				BufferedWriter writer = Files.newBufferedWriter(
					path,
			StandardOpenOption.CREATE,
			StandardOpenOption.TRUNCATE_EXISTING);
			) {
				personToHtml(p).render(writer);
			}

		}
	}
	
	private static DomContent personToHtml(Person p) {
		return html(
			head(
				title(
					text(personName(p).orElse("no name!"))
				)
			),
			body(
				h1(
					text(personName(p).orElse("no name!"))
				)
			)
		);
	}
	
	private static Optional<String> personName(Person p) {
	List<Name> names = p.getNames();
	return Optional.ofNullable(
		names.size() > 0 ? names.get(0).getDisplayValue() : null);
	}
	
	private static String personToFilename(Person p) {
		StringBuilder filenameBuilder = new StringBuilder(p.getId());
		personName(p).ifPresent(name ->
			filenameBuilder
				.append("_")
				.append(name.replaceAll("[^a-zA-Z]", "")));
		filenameBuilder.append(".html");
		return filenameBuilder.toString();
	}
}
