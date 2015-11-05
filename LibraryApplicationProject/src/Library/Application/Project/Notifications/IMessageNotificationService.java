/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Notifications;

import java.util.List;

/**
 *
 * @author Prishu
 */
public interface IMessageNotificationService {
    
    void AddMessageNotification(String patronId,String masterMessageId);
    List<List<String>> GetMessageNotification(String patronId);
    String ShowMessage(String masterMessageId);
    
}
