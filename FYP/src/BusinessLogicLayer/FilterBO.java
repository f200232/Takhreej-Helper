package BusinessLogicLayer;

import CustomException.ResearchAlreadyExistsException;
import DataAccessLayer.IFascadedao;

/**
 *
 * @author Absar Ali
 */
public class FilterBO implements IFilterBO {

    IFascadedao fascadeDAO;

    public FilterBO(IFascadedao fascade) {
        fascadeDAO = fascade;
    }

    @Override
    public boolean createFilter(int researchId, int orderNo, String expression) {
        try {
            if (fascadeDAO.insertFilter(researchId,orderNo,expression)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean updateFilterExpression(int id,int orderNo, String expression) {
        try {
            if (fascadeDAO.updateFilter(id,orderNo, expression)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteFilter(int id) {
        try {
            if (fascadeDAO.deleteFilter(id)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}