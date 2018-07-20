package ua.training.tts.model.service;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.General;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.model.dao.ProjectDao;
import ua.training.tts.model.dao.factory.DaoFactory;
import ua.training.tts.model.entity.Project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ProjectService {

    private ResourceBundle regexpBundle;
    private ProjectDao dao = DaoFactory.getInstance().createProjectDao();

    /**
     * Builds Project entity from the data passed within user's http servlet request
     * @param request       User's request from his browser
     * @return              Project entity built from user's data
     */
    public Project buildProject(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter(TableParameters.PROJECT_ID));
        String name = request.getParameter(TableParameters.PROJECT_NAME);
        LocalDate deadline = LocalDate.parse(request.getParameter(TableParameters.PROJECT_DEADLINE));
        Project.Status status = Project.Status.valueOf(request.getParameter(TableParameters.PROJECT_STATUS)
                                                        .toUpperCase());

        return new Project(id, name, deadline, status);
    }

    /**
     * Builds new Project entity from the data passed within user's http servlet request. As it's a new Project,
     * it doesn't have any Id associated with it.
     * @param request       User's request from his browser
     * @return              Project entity built from user's data
     */
    public Project buildNewProject(HttpServletRequest request){
        String name = request.getParameter(TableParameters.PROJECT_NAME);
        LocalDate deadline = LocalDate.parse(request.getParameter(TableParameters.PROJECT_DEADLINE));
        Project.Status status = Project.Status.valueOf(request.getParameter(TableParameters.PROJECT_STATUS)
                                                        .toUpperCase());

        return new Project(name, deadline, status);
    }

    /**
     * Checks validity of project data then tries to put this data into database
     * @param project       Project entity built from user data
     * @param request       User's request from his browser
     */
    public void tryToPutProjectDataIntoDB(Project project, HttpServletRequest request) {
        if (isDataCorrect(project, request)) {
                sendReadyProjectDataToDB(project);
        }
        else {
            throw new RuntimeException(ExceptionMessages.BAD_NEW_PROJECT_DATA);
        }
    }

    /**
     * Checks validity of project update data then tries to put this data into database
     * @param project       Project entity built from user's update data
     * @param request       User's request from his browser
     */
    public void tryToPutUpdatedProjectDataIntoDB(Project project, HttpServletRequest request) {
        if (isDataCorrect(project, request)) {
                sendReadyUpdatedDataToDB(project);
        }
        else {
            throw new RuntimeException(ExceptionMessages.BAD_UPDATE_PROJECT_DATA);
        }
    }

    /**
     * Matches user's data with regexps
     * @param project      Project entity built from user's data
     * @param request       User's request from his browser
     * @return      True if data is valid, false otherwise
     */
    private boolean isDataCorrect(Project project, HttpServletRequest request) {
        bundleInitialization(request);
        boolean check = (project.getId() == null || matchInputWithRegexp(String.valueOf(project.getId()),
                regexpBundle.getString(TableParameters.PROJECT_ID)));
        check &= matchInputWithRegexp(project.getName(), regexpBundle.getString(TableParameters.PROJECT_NAME));
        check &= matchInputWithRegexp(String.valueOf(project.getDeadline()),
                regexpBundle.getString(TableParameters.PROJECT_DEADLINE));
        check &= matchInputWithRegexp(project.getStatus().name(),
                regexpBundle.getString(TableParameters.PROJECT_STATUS));
        return check;
    }

    /**
     * Initializes Resource Bundles with respective locale
     *
     * @param request       User's request from his browser
     */
    private void bundleInitialization(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = session.getAttribute(ReqSesParameters.LANGUAGE) == null ?
                General.ENGLISH : (String)session.getAttribute(ReqSesParameters.LANGUAGE);
        regexpBundle = ResourceBundle.getBundle(General.REGEXP_BUNDLE_NAME, new Locale(locale));
    }

    /**
     * Applies regexp to user input data to check its validity
     *
     * @param input     User data
     * @param regexp        Respective regexp to be applied to input data
     * @return      True if data pass regexp, false otherwise
     */
    private boolean matchInputWithRegexp(String input, String regexp) {
        return Pattern.matches(regexp, input);
    }

    /**
     * Sends checked data to be put into DB.
     * @param project       Project entity built from user's data
     */
    private void sendReadyProjectDataToDB(Project project) {
        dao.create(project);
    }

    /**
     * Sends checked data to user to be put into DB.
     * @param project       Project entity built from user's data
     */
    public void sendReadyUpdatedDataToDB(Project project) {
        dao.update(project);
    }

    public Project findById(Integer id){
        return dao.findById(id);
    }

    public List<Project> findAll(){
        return dao.findAll();
    }

    public List<Project> findAllActive(){
        return dao.findAllActive();
    }

    public List<Project> findAllArchived(){
        return dao.findAllArchived();
    }

    public List<Project> findAllByStatus(String status){
        return dao.findAllByStatus(status);
    }

    public void deleteById(Integer id){
        dao.delete(id);
    }
}