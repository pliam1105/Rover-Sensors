using RoverSensors.Views;
using System;
using Windows.System;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0x409

namespace RoverSensors
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public static string last_item_tag;       

        public static MainPage GetCurrent()
        {
            Frame appFrame = Window.Current.Content as Frame;
            return appFrame.Content as MainPage;
        }

        public void SetSelectedNavigationItem(int index)
        {
            NavigationView.SelectedItem = NavigationView.MenuItems[index];
        }

        public void ShowUrlTip()
        {
            InvalidUrlTip.Target = NavigationView.SettingsItem as FrameworkElement;
            InvalidUrlTip.IsOpen = true;
        }

        public void ShowSelectTip()
        {
            SelectMissionTip.IsOpen = true;
        }

        public MainPage()
        {
            this.InitializeComponent();

            NavigationView.SelectedItem = NavigationView.MenuItems[0];
            ContentFrame.Navigate(typeof(HomePage));
            last_item_tag = "home";
        }

        private void NavigationView_ItemInvoked(Microsoft.UI.Xaml.Controls.NavigationView sender, Microsoft.UI.Xaml.Controls.NavigationViewItemInvokedEventArgs args)
        {

            if (args.IsSettingsInvoked)
            {
                if(last_item_tag == "settings")
                {
                    return;
                }
                //go to settings
                ContentFrame.Navigate(typeof(SettingsPage));
                last_item_tag = "settings";
            }

            var item = args.InvokedItemContainer;
            string item_tag = item.Tag as string;
            if (item == null)
                return;
            if (item_tag == null)
                return;
            if (item_tag == last_item_tag)
                return;

            switch (item_tag)
            {
                case "home":
                    ContentFrame.Navigate(typeof(HomePage));
                    break;
                case "select_mission":
                    ContentFrame.Navigate(typeof(select_mission));
                    break;
                case "create_mission":
                    ContentFrame.Navigate(typeof(create_mission));
                    break;
                case "sensor_data":
                    ContentFrame.Navigate(typeof(sensor_data));
                    break;
                case "planet_info":
                    ContentFrame.Navigate(typeof(planet_info));
                    break;
                case "rover_route":
                    ContentFrame.Navigate(typeof(rover_route));
                    break;
                default:
                    return;
            }

            last_item_tag = item_tag;
        }

        private void NavigationView_SelectionChanged(Microsoft.UI.Xaml.Controls.NavigationView sender, Microsoft.UI.Xaml.Controls.NavigationViewSelectionChangedEventArgs args)
        {

        }

        private void NavigationView_Loaded(object sender, RoutedEventArgs e)
        {

        }

        private void ContentFrame_NavigationFailed(object sender, NavigationFailedEventArgs e)
        {
            throw new Exception("Failed to load Page " + e.SourcePageType.FullName);
        }

        private async void Web_but_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://pliamprojects.000webhostapp.com/rover/"));
        }

        private async void Contact_but_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://pliamprojects.000webhostapp.com/rover/contact"));
        }

        private async void Question_but_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://pliamprojects.000webhostapp.com/rover/questionnaire"));
        }

        private void NavigationView_BackRequested(Microsoft.UI.Xaml.Controls.NavigationView sender, Microsoft.UI.Xaml.Controls.NavigationViewBackRequestedEventArgs args)
        {
            if (ContentFrame.CanGoBack)
            {
                ContentFrame.GoBack();
                string back_page = ContentFrame.SourcePageType.Name;

                if(back_page == "HomePage")
                {
                    NavigationView.SelectedItem = NavigationView.MenuItems[0];
                    return;
                }else if(back_page == "SettingsPage")
                {
                    NavigationView.SelectedItem = NavigationView.SettingsItem;
                    return;
                }
                else if (back_page == "select_mission")
                {
                    NavigationView.SelectedItem = NavigationView.MenuItems[3];
                    return;
                }
                else if (back_page == "create_mission")
                {
                    NavigationView.SelectedItem = NavigationView.MenuItems[4];
                    return;
                }
                else if (back_page == "sensor_data")
                {
                    NavigationView.SelectedItem = NavigationView.MenuItems[7];
                    return;
                }
                else if (back_page == "planet_info")
                {
                    NavigationView.SelectedItem = NavigationView.MenuItems[8];
                    return;
                }
                else if (back_page == "rover_route")
                {
                    NavigationView.SelectedItem = NavigationView.MenuItems[9];
                    return;
                }
            }
        }
    }
}
