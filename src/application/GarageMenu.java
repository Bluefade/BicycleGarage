package application;

	import java.util.Collections;
	import java.util.Optional;
	import javafx.application.Platform;
	import javafx.scene.control.Menu;
	import javafx.scene.control.MenuBar;
	import javafx.scene.control.MenuItem;
	import phonebook.PhoneBook;
	import phonebook.MapPhoneBook;
	import java.util.Map;
	import java.util.Set;
	import java.util.TreeSet;

	public class GarageMenu extends MenuBar {
		private PhoneBook phoneBook;
		private NameListView nameListView;
		
		/** Creates the menu for the phone book application.
		 * @param phoneBook the phone book with names and numbers
		 * @param nameListView handles the list view for the names
		 */
		public PhoneBookMenu(PhoneBook phoneBook, NameListView nameListView) {
			this.phoneBook = phoneBook;
			this.nameListView = nameListView;

			final Menu menuPhoneBook = new Menu("PhoneBook");
			final MenuItem menuQuit = new MenuItem("Quit");
			menuQuit.setOnAction(e -> Platform.exit());
			menuPhoneBook.getItems().addAll(menuQuit);
		
			final Menu menuFind = new Menu("Find");
			
			final MenuItem menuShowAll = new MenuItem("Show All");
			menuShowAll.setOnAction(e -> showAll());
			menuFind.getItems().addAll(menuShowAll);
			
			final MenuItem menuFindNumbers = new MenuItem("Find Number(s)");
			menuFindNumbers.setOnAction(e -> findNumbers());
			menuFind.getItems().addAll(menuFindNumbers);
			
			final MenuItem menuFindNames = new MenuItem("Find Name(s)");
			menuFindNames.setOnAction(e -> findNames());
			menuFind.getItems().addAll(menuFindNames);
			
			final MenuItem menuFindPersons = new MenuItem("Find Person(s)");
			menuFindPersons.setOnAction(e -> findPersons());
			menuFind.getItems().addAll(menuFindPersons);

		    getMenus().addAll(menuPhoneBook, menuFind);
	  //    setUseSystemMenuBar(true);  // if you want operating system rendered menus, uncomment this line
		}

		
		private void showAll() {
			nameListView.fillList(phoneBook.names());
			nameListView.clearSelection();
		}
		
		private void findNumbers() {
			Optional<String> name = Dialogs.oneInputDialog("Namnsökning", "Val av kontakt", "Mata in namnet på den kontakt som sökes");
			if (name.isPresent()) {
				String n = name.get();
				PhoneBook pb = new MapPhoneBook();
				for(String number : phoneBook.findNumbers(n)){
					pb.put(n, number);
				}
				if(pb.isEmpty()){
					if(Dialogs.confirmDialog("Ett fel uppstod","Den valda kontakten har inga tillagda nummer","Vill du testa en annan kontakt?")){
						findNumbers();
					}
				} else{
					nameListView.fillList(pb.names());
					nameListView.select(n);
				}
			}
		}
		private void findNames() {
			Optional<String> number = Dialogs.oneInputDialog("Nummersökning", "Val av nummer", "Mata in numret till den kontakt som sökes");
			if (number.isPresent()) {
				String n = number.get();
				if(Collections.emptySet().equals(phoneBook.findNames(n))){
					if(Dialogs.confirmDialog("Ett fel uppstod","Det valda numret tillhör ingen av de tillagda kontakterna","Vill du testa ett annat nummer?")){
						findNames();
					}
				} else{
					nameListView.fillList(phoneBook.findNames(n));
				}
			}
		}
		
		private void findPersons() {
			Optional<String> seq = Dialogs.oneInputDialog("Namnsökning", "Val av namn", "Mata in de första tecken som namnet som sökes startar på");
				CharSequence n = seq.get();
				Set<String> names = new TreeSet<String>();
				for(String namn : phoneBook.names()){
					if(namn.contains(n)){
						names.add(namn);
					}
				}
				if(Collections.emptySet().equals(names)){
					if(Dialogs.confirmDialog("Ett fel uppstod","Det valda namnet tillhör ingen av de tillagda kontakterna","Vill du testa ett annat namn?")){
						findNames();
					}
				} else{
					nameListView.fillList(names);
				}	
		}
	}
