package AddressBook.bean;

/**
* Program Name: CMIS440 Lab 4 Address Book Web App
* @author Brandon R Russell
* @Course CMIS440
* Date: Jan 6, 2011
* IDE: MyEclipse 9.0 M1
* OS: Windows 7
* Java: Java EE 5, JSF 1.2, JPA 2.0, IceFaces 1.8.1, GlassFish 2.1.1
* Files: 
*
* Program Requirements: 
*
* Program Design: 
*
* Things you what me to know before I grade your work: 
*/

/** This class manages interaction between front-end and back-end classes.
* Basically, this class is considered a Controller, almost like a buffer 
* between all dealings between the front-end website and the back-end database. 
*|----------------------------------------------------------------------------|
*|                           CRC: AddressBean                                 |
*|----------------------------------------------------------------------------|
*|Used to interact with back-end                           AddressBook.jspx   |
*|Used to interact with front-end                          EntityManagerHelper|
*|                                                         AddressesDAO       |
*|Object model for table entries                           Addresses          |
*|Used to keep log of user actions                         Logger             |
*|Extended to provide sorting for front-end table          SortableList       |
*|----------------------------------------------------------------------------|
*
* @TheCs Cohesion - All methods in this class work together on similar task.
* Completeness - Completely interacts with front-end and back-end classes.
* Convenience - There are sufficient methods and variables to complete the
*                required task.
* Clarity - The methods and variables are distinguishable and work in a
*           uniform manner to provide clarity to other programmers.
* Consistency - All names,parameters ,return values , and behaviors follow
*               the same basic rules.
*/



import java.util.*;
import javax.faces.model.SelectItem;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import myPersistance.Addresses;
import myPersistance.AddressesDAO;
import myPersistance.EntityManagerHelper;


public class AddressBean extends SortableList{

	
	AddressesDAO myAddressesDAO = new AddressesDAO();
	Addresses myTableAddress;
	Addresses myNewAddress;
	Addresses mySearchAddress;
	List<Addresses> myAddresses = new ArrayList<Addresses>();
	List<SelectItem> lastNames;
	Logger myLog = new Logger();
	boolean instructionsVisible = false;
	
    

	
    /** Constructor initializes an AddressBean object.
     *@TheCs Cohesion - Initializes an AddressBean object.
	 * Completeness - Completely initializes an AddressBean object.
	 * Convenience - Simply initializes an AddressBean object.
	 * Clarity - It is simple to understand that this initializes an
     *           AddressBean object.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 */	
	public AddressBean() {
		/**
		 * First a call is made to the extended SortableList class to set the 
		 * current sorted column to the 'ID' field. The three address objects
		 * are used to get the values from the front-end when editing a record,
		 * adding a record, and searching for a record. The myAddresses list is
		 * used to provide the table on the front-end with a list of address
		 * objects.
		 */
		super("ID");
		myTableAddress = new Addresses();
		myNewAddress = new Addresses();
		mySearchAddress = new Addresses();
		myAddresses = myAddressesDAO.findAll();
	}

    /** Retrieves the current address object used by the table.
     *@TheCs Cohesion - Retrieves the current address object used by the table.
	 * Completeness - Completely retrieves the current address object used by 
	 * 				  the table.
	 * Convenience - Simply retrieves the current address object used by the table.
	 * Clarity - It is simple to understand that this retrieves the current 
	 *           address object used by the table.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @return myTableAddress object               
	 */		
	public Addresses getCurrentTableAddress(){
		return myTableAddress;
	}

    /** Sets the current address object used by the table.
     *@TheCs Cohesion - Sets the current address object used by the table.
	 * Completeness - Completely sets the current address object used by 
	 * 				  the table.
	 * Convenience - Simply sets the current address object used by the table.
	 * Clarity - It is simple to understand that this sets the current 
	 *           address object used by the table.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @param tableAddress sets the table address object to another address object              
	 */			
	public void setCurrentTableAddress(Addresses tableAddress){
		myTableAddress = tableAddress;
	}
		
    /** Retrieves the current address object used to add a record.
     *@TheCs Cohesion - Retrieves the current address object used to add a record.
	 * Completeness - Completely retrieves the current address object used to 
	 *                add a record.
	 * Convenience - Simply retrieves the current address object used to add a 
	 *               record.
	 * Clarity - It is simple to understand that this retrieves the current 
	 *           address object to add a record.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @return myNewAddress object               
	 */			
	public Addresses getCurrentNewAddress(){
		return myNewAddress;
	}

    /** Sets the current address object used to add a record.
     *@TheCs Cohesion - Sets the current address object used to add a record.
	 * Completeness - Completely sets the current address object used to add
	 *                a record.
	 * Convenience - Simply sets the current address object used to add a record.
	 * Clarity - It is simple to understand that this sets the current 
	 *           address object used to add a record.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @param newAddress sets the new address object to another address object              
	 */	
	public void setCurrentNewAddress(Addresses newAddress){
		myNewAddress = newAddress;
	}	

    /** Retrieves the current address object used to search for a record.
     *@TheCs Cohesion - Retrieves the current address object used to 
     *                  search for a record.
	 * Completeness - Completely retrieves the current address object used to 
	 *                search for a record.
	 * Convenience - Simply retrieves the current address object used to search
	 *               for a record.
	 * Clarity - It is simple to understand that this retrieves the current 
	 *           address object to search for a record.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @return mySearchAddress object               
	 */		
	public Addresses getCurrentSearchAddress(){
		return mySearchAddress;
	}
	
    /** Sets the current address object used to search for a record.
     *@TheCs Cohesion - Sets the current address object used to search for a 
     *                  record.
	 * Completeness - Completely sets the current address object used to search
	 *                for a record.
	 * Convenience - Simply sets the current address object used to search for
	 *               a record.
	 * Clarity - It is simple to understand that this sets the current 
	 *           address object used to search for a record.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @param searchAddress sets the search address object to another address object              
	 */		
	public void setCurrentSearchAddress(Addresses searchAddress){
		mySearchAddress = searchAddress;
	}	

    /** Sets the current list of addresses to another list of addresses.
     *@TheCs Cohesion - Sets the current list of addresses to another list
     *                  of addresses.
	 * Completeness - Completely sets the current list of addresses to another 
	 *                list of addresses.
	 * Convenience - Simply sets the current list of addresses to another list
	 *               of addresses.
	 * Clarity - It is simple to understand that this sets the current list of
	 *           addresses to another list of addresses.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @param addressesDAO set the addresses list to another addresses list              
	 */			
    public void setAddressDao(AddressesDAO addressesDAO) {
        myAddressesDAO = addressesDAO;
    }
	 
	
    /** Adds a new address record to the database.
     *@TheCs Cohesion - Adds a new address record to the database.
	 * Completeness - Completely adds a new address record to the database.
	 * Convenience - Simply adds a new address record to the database.
	 * Clarity - It is simple to understand that this adds a new address record
	 *           to the database.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @exception Exception general exception capture      
	 * @return success or failure depending on if transaction goes through        
	 */			    
	public String addAddress(){
		try{
			if (myNewAddress.getCity().equals("") || 
					myNewAddress.getEmailaddress().equals("") || 
					myNewAddress.getFirstname().equals("") || 
					myNewAddress.getLastname().equals("") ||
					myNewAddress.getPhonenumber().equals("") || 
					myNewAddress.getState().equals("") ||
					myNewAddress.getStreet().equals("") ||
					myNewAddress.getZip().equals("")){
				/**
				 * Here, if any of the fields are left blank then it creates a 
				 * new message to be displayed on the web site and returns instead
				 * of attempting to add a record since all of the fields are 
				 * required.
				 */
				
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage msg = 
					new FacesMessage("Everything with an asterisk* is required!");
				context.addMessage("addForm:Add", msg);
				return "failure";
			}
			/**
			 * First the EntityManagerHelper is called to initiate the transaction.
			 * Then, the AddressesDAO object save method is called to persist the
			 * new address object to the database and the EntityManagerHelper
			 * is called again to commit the changes.
			 */
			EntityManagerHelper.beginTransaction();
			myAddressesDAO.save(myNewAddress);
			EntityManagerHelper.commit();
			/**
			 * myLog is called to write to a file that the add was successful and
			 * then the clear method is called to clear out all the objects and
			 * renew the list.
			 */
			myLog.log("ID: " + myNewAddress.getAddressid() 
					+ " Successfully added into database");
			clear(null);
			return "success";
			}catch (Exception exception){
				myLog.log(exception.getMessage());
				return "failure";
			}
	}

    /** Updates a address record in the database.
     *@TheCs Cohesion - Updates a address record in the database.
	 * Completeness - Completely updates a address record in the database.
	 * Convenience - Simply updates a address record in the database.
	 * Clarity - It is simple to understand that this updates a address record
	 *           in the database.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @param evt ActionEvent               
	 * @exception Exception general exception capture              
	 */		
	public void updateAddress(ActionEvent evt){
		try{
			if (myTableAddress.getCity().equals("") || 
					myTableAddress.getEmailaddress().equals("") || 
					myTableAddress.getFirstname().equals("") || 
					myTableAddress.getLastname().equals("") ||
					myTableAddress.getPhonenumber().equals("") || 
					myTableAddress.getState().equals("") ||
					myTableAddress.getStreet().equals("") ||
					myTableAddress.getZip().equals("")){
				
				/**
				 * Here, if any of the fields are left blank then it creates a 
				 * new message to be displayed on the web site and returns instead
				 * of attempting to update a record since all of the fields are 
				 * required.
				 */				
				
				 FacesContext context = FacesContext.getCurrentInstance();
				 FacesMessage msg = 
					 new FacesMessage("All fields must be filled before updated!");
				 context.addMessage("editForm:Edit", msg);				
				return;
			}				
					
			/**
			 * First the EntityManagerHelper is called to initiate the transaction.
			 * Then, the AddressesDAO object update method is called to merge the
			 * changed address object to the database and the EntityManagerHelper
			 * is called again to commit the changes.
			 */			
			EntityManagerHelper.beginTransaction();
			myAddressesDAO.update(myTableAddress);
			EntityManagerHelper.commit();
			/**
			 * myLog is called to write to a file that the update was successful and
			 * then the cancelEdit method is called to clear out all table
			 * address objects and also sets the editable property to false to
			 * indicate that the table should not have any editable fields.
			 */			
			myLog.log("ID: " + myTableAddress.getAddressid() 
					+ " Successfully updated in database");
			cancelEdit(null);
			
		}catch (Exception exception){
			myLog.log(exception.getMessage());
		}		
	}
	
    /** Deletes a address record from the database.
     *@TheCs Cohesion - Deletes a address record from the database.
	 * Completeness - Completely deletes a address record from the database.
	 * Convenience - Simply deletes a address record from the database.
	 * Clarity - It is simple to understand that this deletes a address record
	 *           from the database.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @param evt ActionEvent               
	 * @exception Exception general exception capture              
	 */		
	public void deleteAddress(ActionEvent evt){
		try{
			
			/**
			 * First the EntityManagerHelper is called to initiate the transaction.
			 * Then, the AddressesDAO object delete method is called to remove the
			 * address object from the database and the EntityManagerHelper
			 * is called again to commit the changes.
			 */				
			EntityManagerHelper.beginTransaction();
			myAddressesDAO.delete(myTableAddress);
			EntityManagerHelper.commit();
			/**
			 * myLog is called to write to a file that the delete was successful and
			 * then the cancelEdit method is called to clear out all table
			 * address objects and also sets the editable property to false to
			 * indicate that the table should not have any editable fields.
			 */				
			myLog.log("ID: " + myTableAddress.getAddressid() 
					+ " Successfully deleted from database");
			cancelEdit(null);
			myAddresses = new AddressesDAO().findAll();
		}catch (Exception exception){
			myLog.log(exception.getMessage());
		}		
	}		
	
	
    /** Returns list of addresses objects for front-end table.
     *@TheCs Cohesion - Returns list of addresses objects for front-end table.
	 * Completeness - Completely returns list of addresses objects for front-end table.
	 * Convenience - Simply returns list of addresses objects for front-end table.
	 * Clarity - It is simple to understand that this returns list of addresses 
	 *           objects for front-end table.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @return myAddresses List                              
	 * @exception Exception general exception capture              
	 */		
	public List<Addresses> getAllAddresses(){

		try{
			/**
			 * First, if mySearchAddress object doesn't have a first name set then do
			 * nothing to myAddresses List since it will already have in it at this point
			 * a current copy of all of the addresses objects. Else use the .findByLastname
			 * method of the AddressesDAO object to populate the list with all records that
			 * match the last name entered.
			 */
			if ( mySearchAddress.getLastname() == null ||
					mySearchAddress.getLastname().equals("")){
				
			}else{
				myAddresses =  
					myAddressesDAO.findByLastname(mySearchAddress.getLastname());
			}
			/**
			 * If the user has changed the column to sort on or if they have changed the
			 * direction of the sorting then call the sort method to sort the addresses
			 * before returning them.
			 */
			if (!oldSort.equals(sortColumnName) ||
					oldAscending != ascending){
					sort();
					oldSort = sortColumnName;
					oldAscending = ascending;
			}
			return myAddresses;
        }catch (Exception exception){
        	myLog.log(exception.getMessage());
        	return myAddresses;
        }
        
	}
	
    /** Provides auto-complete from database for search field.
     *@TheCs Cohesion - Provides auto-complete from database for search field.
	 * Completeness - Completely provides auto-complete from database for search field.
	 * Convenience - Simply provides auto-complete from database for search field.
	 * Clarity - It is simple to understand that this provides auto-complete from 
	 *           database for search field.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
	 * @param event a ValueChangeEvent linked to the search field on the front-end                                        
	 * @exception Exception general exception capture              
	 */		
	public void getAddressesByLastName(ValueChangeEvent event){
		/**
		 * Whenever the user types something into the search field on the
		 * front-end this method is called.
		 */
		try{			
			/**
			 * This will take what the user typed into the searchWord object and also
			 * refresh the myAddresses list to ensure a complete list is being used.
			 */
			lastNames = new ArrayList<SelectItem>();
			myAddresses = new AddressesDAO().findAll();
			Object searchWord = event.getNewValue();
			for(int i = 0; i < myAddresses.size(); i++){
				/**
				 * If for some reason the last name for this addresses object is
				 * blank then do nothing with it. Else if the length of the addresses
				 *  last name is less than what the user has typed do nothing since this
				 *  won't be useful. Finally, take a substring of the addresses last name
				 *  so that it equals the length of the search word starting from its 
				 *  first character to determine if the two are equal. If so, and if the
				 *  lastNames list doesn't already contain this last name then add it to
				 *  the list.
				 */
				if (myAddresses.get(i).getLastname().length() == 0){
					
				}else if (myAddresses.get(i).getLastname().length() < 
						searchWord.toString().length()){
				
				}else if(myAddresses.get(i).getLastname()
						.substring(0,searchWord.toString().length())
						.equalsIgnoreCase(searchWord.toString())){
					
					if (!lastNames.contains(myAddresses.get(i).getLastname())){
						lastNames.add(
								new SelectItem(myAddresses.get(i).getLastname(),
										myAddresses.get(i).getLastname()));
					}
					
				}
			}
        }catch (Exception exception){
        	myLog.log(exception.getMessage());
        }		
		
	}
	
    /** Returns a list of last names for search field autocomplete.
     *@TheCs Cohesion - Returns a list of last names for search field autocomplete.
	 * Completeness - Completely Returns a list of last names for search field autocomplete.
	 * Convenience - Simply Returns a list of last names for search field autocomplete.
	 * Clarity - It is simple to understand that this Returns a list of last names 
	 *           for search field autocomplete.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @return lastNames a list of last names that match search field.         
	 */		
	public List<SelectItem> getLastNameMatches(){
		return lastNames;
	}
	
    /** Renews Addresses objects, refills myAddresses list, and undo's edit on table.
     *@TheCs Cohesion - Renews Addresses objects, refills myAddresses list, and undo's 
     *                  edit on table.
	 * Completeness - Completely renews Addresses objects, refills myAddresses list, 
	 *                and undo's edit on table.
	 * Convenience - Simply Renews Addresses objects, refills myAddresses list, and
	 *               undo's edit on table.
	 * Clarity - It is simple to understand that this Renews Addresses objects, 
	 *           refills myAddresses list, and undo's edit on table.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @param evt ActionEvent         
	 */		
	public void clear(ActionEvent evt){
		myNewAddress = new Addresses();
		mySearchAddress = new Addresses();
		myAddresses = myAddressesDAO.findAll();
		cancelEdit(null);
	}
	
    /** Disables editable field on front-end table.
     *@TheCs Cohesion - Disables editable field on front-end table.
	 * Completeness - Completely disables editable field on front-end table.
	 * Convenience - Simply disables editable field on front-end table.
	 * Clarity - It is simple to understand that this disables editable field 
	 *           on front-end table.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @param evt ActionEvent         
	 */		
	public void cancelEdit(ActionEvent evt){
		myTableAddress.setEditable(false);
		myTableAddress = new Addresses();
		
	}
	
    /** Makes a record on the front-end table editable.
     *@TheCs Cohesion - Makes a record on the front-end table editable.
	 * Completeness - Completely makes a record on the front-end table editable.
	 * Convenience - Simply makes a record on the front-end table editable.
	 * Clarity - It is simple to understand that this makes a record on 
	 *           the front-end table editable.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @param evt ActionEvent
     * @exception Exception general exception capture         
	 */		
	public void editAction(ActionEvent evt) {
		try{
			/**
			 * If another record is already being edited then return here and do nothing.
			 */
			if (myTableAddress.getAddressid()!= null){
				return;
			}		
			/**
			 * Set the myTableAddress object to the record that was clicked to be edited
			 * by using the AddressDAO findbyID method to find the record. It will retrieve
			 * the actually ID from the 'theAddressID' attribute set on the front-end.
			 */
			myTableAddress = 
				myAddressesDAO.findById(
						(Long) evt.getComponent().getAttributes()
						.get("theAddressID"));
			/**
			 * Make the field editable.
			 */
			myTableAddress.setEditable(true);
		
		}catch (Exception exception){
        	myLog.log(exception.getMessage());
        }
	}
	
    /** Validates email address field.
     *@TheCs Cohesion - Validates email address field.
	 * Completeness - Completely validates email address field.
	 * Convenience - Simply validates email address field.
	 * Clarity - It is simple to understand that this validates email address field.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @param context FacesContext used to add message to front-end message object
     * @param validate UIComponent the UI Component this is for
     * @param value Object the text entered into the email address field
     * @exception Exception general exception capture         
	 */		
    public void validateEmail(FacesContext context, 
    		UIComponent validate, Object value){
    	try{
    
    		String email = (String)value;

    		/**
    		 * If the email address field doesn't contain a @ then add a message
    		 * to let the user know they didn't enter a correct e-mail. Also checks
    		 * the length.
    		 */
    		if(email.indexOf('@')==-1 || email.length() > 50){
    			((UIInput)validate).setValid(false);
    			FacesMessage msg = new FacesMessage("Invalid Email entered");
    			context.addMessage(validate.getClientId(context), msg);
    		}
        
        }catch (Exception exception){
        	myLog.log(exception.getMessage());
        }        
    }
    
    /** Validates phone number field.
     *@TheCs Cohesion - Validates phone number field.
	 * Completeness - Completely validates phone number field.
	 * Convenience - Simply validates phone number field.
	 * Clarity - It is simple to understand that this validates phone number field.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @param context FacesContext used to add message to front-end message object
     * @param validate UIComponent the UI Component this is for
     * @param value Object the text entered into the email address field
     * @exception Exception general exception capture         
	 */	    
    public void validatePhone(FacesContext context, 
    		UIComponent validate, Object value){
    	try{
    		String phone = (String)value;
    		/**
    		 * If the phone number field is blank or if it's length is greater than 30
    		 * then add a message to the front-end to let the user know they entered it
    		 * wrong.
    		 */
    		if(phone.equals("") || phone.length() > 30){
    			((UIInput)validate).setValid(false);
    			FacesMessage msg = new FacesMessage("Phone Number is incorrect");
    			context.addMessage(validate.getClientId(context), msg);
    		}
        
        }catch (Exception exception){
        	myLog.log(exception.getMessage() + "\n");
        }        
    }
    
    /** Validates all other input fields.
     *@TheCs Cohesion - Validates all other input fields.
	 * Completeness - Completely validates all other input fields.
	 * Convenience - Simply validates all other input fields.
	 * Clarity - It is simple to understand that this validates all other input fields.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @param context FacesContext used to add message to front-end message object
     * @param validate UIComponent the UI Component this is for
     * @param value Object the text entered into the email address field
     * @exception Exception general exception capture         
	 */	    
    public void validateAllOthers(FacesContext context, UIComponent validate, Object value){
    	try{
    
    		/**
    		 * Each of the else if statements check the clientID of the component in 
    		 * question and will check the size to make sure none of the fields are
    		 * larger than what is allowed by the database.
    		 */    		
    		String allOther = (String)value;

    		if(allOther.equals("")){
    			((UIInput)validate).setValid(false);
    			FacesMessage msg = new FacesMessage("Everything with an asterisk* is required!");
    			context.addMessage(validate.getClientId(context), msg);
    		}else if(validate.getClientId(context).toLowerCase().contains("lastname") && 
			allOther.length() > 30){
			FacesMessage msg = 
				new FacesMessage("Last Name is incorrect");
			context.addMessage(validate.getClientId(context), msg);    			
		}else if(validate.getClientId(context).toLowerCase().contains("firstname") &&
				allOther.length() > 30){
			FacesMessage msg = 
				new FacesMessage("First Name is incorrect");
			context.addMessage(validate.getClientId(context), msg);    			
		}else if(validate.getClientId(context).toLowerCase().contains("street") &&
				allOther.length() > 150){
			FacesMessage msg = 
				new FacesMessage("Street is incorrect");
			context.addMessage(validate.getClientId(context), msg);    			
		}else if(validate.getClientId(context).toLowerCase().contains("city") &&
				allOther.length() > 30){
			FacesMessage msg = 
				new FacesMessage("City is incorrect");
			context.addMessage(validate.getClientId(context), msg);    			
		}else if(validate.getClientId(context).toLowerCase().contains("State") &&
				allOther.length() > 2){
			FacesMessage msg = 
				new FacesMessage("State is incorrect");
			context.addMessage(validate.getClientId(context), msg);    			
		}else if(validate.getClientId(context).toLowerCase().contains("zip") &&
				allOther.length() > 5){
			FacesMessage msg = 
				new FacesMessage("Zip Code is incorrect");
			context.addMessage(validate.getClientId(context), msg);    			
		}
        
        }catch (Exception exception){
        	myLog.log(exception.getMessage());
        }        
    }   
    

    
    /** Sets default order to ascending.
     *@TheCs Cohesion - Sets default order to ascending.
	 * Completeness - Completely sets default order to ascending.
	 * Convenience - Simply sets default order to ascending.
	 * Clarity - It is simple to understand that this sets default order to ascending.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @param   sortColumn to sort by ascending
     * @return  true to setup ascending
     */
    protected boolean isDefaultAscending(String sortColumn) {
        return true;
    }

    /** Sorts myAddresses, a list of addresses objects.
     *@TheCs Cohesion - Sorts myAddresses, a list of addresses objects.
	 * Completeness - Completely sorts myAddresses, a list of addresses objects.
	 * Convenience - Simply sorts myAddresses, a list of addresses objects.
	 * Clarity - It is simple to understand that this sorts myAddresses, a 
	 *           list of addresses objects.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @exception Exception general exception capture
     */    
    protected void sort() {
    	try{
    		/**
    		 * This creates a comparator object that will be used to sort the current
    		 * myAddress list of addresses.
    		 */
    		Comparator<Object> comparator = new Comparator<Object>() {
    			public int compare(Object o1, Object o2) {
    				Addresses c1 = (Addresses) o1;
    				Addresses c2 = (Addresses) o2;
    				if (sortColumnName == null) {
    					return 0;
    				}
    				/**
    				 * Basically, it first determines which field is currently selected for
    				 * sorting, then returns how to compare the addresses object depending on
    				 * whether it is being sorted ascending or not.
    				 */
    				if (sortColumnName.equals("ID")) {
    					return ascending ?
    							new Long(c1.getAddressid()).compareTo(new Long(c2.getAddressid())) :
    							new Long(c2.getAddressid()).compareTo(new Long(c1.getAddressid()));
    				} else if (sortColumnName.equals("First Name")) {
    					return ascending ? c1.getFirstname().compareTo(c2.getFirstname()) :
    						c2.getFirstname().compareTo(c1.getFirstname());
    				} else if (sortColumnName.equals("Last Name")) {
    					return ascending ? c1.getLastname().compareTo(c2.getLastname()) :
                            c2.getLastname().compareTo(c1.getLastname());
    				} else if (sortColumnName.equals("Street")) {
    					return ascending ?
                            c1.getStreet().compareTo(c2.getStreet()) :
                            c2.getStreet().compareTo(c1.getStreet());
    				} else if (sortColumnName.equals("City")) {
    					return ascending ?
                            c1.getCity().compareTo(c2.getCity()) :
                            c2.getCity().compareTo(c1.getCity());
    				}  else if (sortColumnName.equals("State")) {
    					return ascending ?
                            c1.getState().compareTo(c2.getState()) :
                            c2.getState().compareTo(c1.getState());
    				} else if (sortColumnName.equals("Zip Code")) {
    					return ascending ?
                            c1.getZip().compareTo(c2.getZip()) :
                            c2.getZip().compareTo(c1.getZip());
    				} else if (sortColumnName.equals("Email Address")) {
    					return ascending ?
                            c1.getEmailaddress().compareTo(c2.getEmailaddress()) :
                            c2.getEmailaddress().compareTo(c1.getEmailaddress());
    				} else if (sortColumnName.equals("Phone Number")) {
    					return ascending ?
                            c1.getPhonenumber().compareTo(c2.getPhonenumber()) :
                            c2.getPhonenumber().compareTo(c1.getPhonenumber());
    				} else return 0;
    				}
    			};
    			Collections.sort(myAddresses, comparator);
        
        }catch (Exception exception){
        	myLog.log(exception.getMessage());
        }        
        
    }
    
    /** Closes the Instructions Popup by setting it's boolean to false.
     *@TheCs Cohesion - Closes the Instructions Popup by setting it's boolean to false.
	 * Completeness - Completely closes the Instructions Popup by setting it's boolean
	 *                to false.
	 * Convenience - Simply closes the Instructions Popup by setting it's boolean
	 *                to false.
	 * Clarity - It is simple to understand that this closes the Instructions Popup by 
	 *           setting it's boolean to false.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @return success
     */ 	
    public String closePopup() {
        instructionsVisible = false;
        return "success";
    }
    
    /** Opens the Instructions Popup by setting it's boolean to true.
     *@TheCs Cohesion - Opens the Instructions Popup by setting it's boolean to true.
	 * Completeness - Completely opens the Instructions Popup by setting it's boolean
	 *                to true.
	 * Convenience - Simply opens the Instructions Popup by setting it's boolean
	 *                to true.
	 * Clarity - It is simple to understand that this opens the Instructions Popup by 
	 *           setting it's boolean to true.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @return success
     */     
    public String openPopup() {
        instructionsVisible = true;
        return "success";
    }

    /** Returns the current boolean value for the instructions Popup.
     *@TheCs Cohesion - Returns the current boolean value for the instructions Popup.
	 * Completeness - Completely returns the current boolean value for the instructions
	 *                Popup.
	 * Convenience - Simply returns the current boolean value for the instructions Popup.
	 * Clarity - It is simple to understand that this returns the current boolean value 
	 *           for the instructions Popup.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @return instructionsVisible boolean
     */      
    public boolean getPopupVisible(){
    	return instructionsVisible;
    }
    
    /** Returns a string of instructions for using the front-end.
     *@TheCs Cohesion - Returns a string of instructions for using the front-end.
	 * Completeness - Completely returns a string of instructions for using the front-end.
	 * Convenience - Simply returns a string of instructions for using the front-end.
	 * Clarity - It is simple to understand that this returns a string of instructions 
	 *           for using the front-end.
	 * Consistency - It uses the same syntax rules as the rest of the class and
	 *               continues to use proper casing and indentation.
     * @return a string of instructions
     */      
    public String getInstructions(){
    	return "<b>Add Address:</b> <br />" +
    	"1) Enter in all info with an asterisk next to it. <br />" +
    	"2) Click the 'Add' button. <br />" +
    	"3) Click the 'Clear button to reset all fields. <br />" +
    	"<b>Search by last name:</b> <br />" +
    	"1) Begin entering the last name you want to find, the autocomplete will show matching last names. <br />" +
    	"2) Once you find the last name or have it entered press the 'Enter' key and all records with this last name <br />" +
    	"   will appear in the table. <br />" +
    	"3) Clear the search field to show all records. <br />" + 
    	"<b>Edit/Delete Addresses:</b> <br />" +
    	"1) Either find by searching or scrolling through the table to find the record you want to edit. <br />" +
    	"2) Once found click the 'Edit' button next to the record and the fields will become editable. <br />" +
    	"3) Once you make your changes click the 'Save' button or the 'Delete button to delete the record. <br />" + 
    	"4) You can also click the 'Cancel' button to not update the record. <br />" +
    	"*Note - Only one record can be edited at a time.";
    }

}

















