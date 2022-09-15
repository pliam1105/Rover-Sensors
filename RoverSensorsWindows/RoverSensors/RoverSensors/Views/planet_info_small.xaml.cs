using System;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Windows.Data.Json;
using Windows.System;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media.Imaging;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class planet_info_small : Page
    {
        public string planet_name;
        public string planet_type;
        public string planet_mass;
        public string planet_radius;
        public string planet_flux;
        public string planet_teq;
        public string planet_period;
        public string planet_distance;
        public string planet_esi;
        public string planet_id;
        public object planet_favorite;
        public BitmapImage planet_image;

        public Thickness habitability_panel_padding;
        public planet_info_small()
        {
            this.InitializeComponent();
        }

        private void InfoHeaderGrid_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            PositionHeader();
        }

        public void PositionHeader()
        {
            if (InfoHeaderGrid.ActualWidth < ImgNameGrid.ActualWidth + HabitabilityPanel.ActualWidth + 60)
            {
                //drop second element below the first
                InfoHeaderGrid.ColumnDefinitions[0].Width = new GridLength(1, GridUnitType.Star);
                InfoHeaderGrid.ColumnDefinitions[1].Width = new GridLength(0, GridUnitType.Pixel);
                Grid.SetColumn(HabitabilityPanel, 0);
                Grid.SetRow(HabitabilityPanel, 1);
                ImgNameGrid.HorizontalAlignment = HorizontalAlignment.Center;
                HabitabilityPanel.HorizontalAlignment = HorizontalAlignment.Center;
                //Change habitability panel panel right padding to 0
                habitability_panel_padding = new Thickness();
                habitability_panel_padding.Top = 20;
                habitability_panel_padding.Right = 60;
                habitability_panel_padding.Left = 60;
                HabitabilityPanel.Padding = habitability_panel_padding;
            }
            else
            {
                //return to the default state
                InfoHeaderGrid.ColumnDefinitions[0].Width = GridLength.Auto;
                InfoHeaderGrid.ColumnDefinitions[1].Width = new GridLength(1, GridUnitType.Star);
                Grid.SetColumn(HabitabilityPanel, 1);
                Grid.SetRow(HabitabilityPanel, 0);
                ImgNameGrid.HorizontalAlignment = HorizontalAlignment.Left;
                HabitabilityPanel.HorizontalAlignment = HorizontalAlignment.Right;
                //Change habitability panel panel right padding to 60
                habitability_panel_padding = new Thickness();
                habitability_panel_padding.Top = 20;
                habitability_panel_padding.Right = 60;
                habitability_panel_padding.Left = 0;
                HabitabilityPanel.Padding = habitability_panel_padding;
            }
        }

        private async void Page_Loaded(object sender, RoutedEventArgs e)
        {
            PositionHeader();
            await LoadPlanetInfo();
        }

        public void PositionPopup()
        {
            //Stretch popup
            InfoPopupGrid.Width = InfoGrid.ActualWidth;
            InfoPopupGrid.Height = (Window.Current.Bounds.Height - 20) * 2 / 5;

            InfoPopupBorder.Width = InfoGrid.ActualWidth;
            InfoPopupBorder.Height = (Window.Current.Bounds.Height - 20) * 2 / 5;

            InfoPopup.Width = InfoGrid.ActualWidth;
            InfoPopup.Height = (Window.Current.Bounds.Height - 20) * 2 / 5;
        }

        public async Task<string> GetUrlAsync()
        {
            string url;
            //initialize url
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            object _fullurl = localSettings.Values["fullurl"];
            if (_fullurl != null)
            {
                string fullurl = _fullurl as string;
                if (fullurl.Count() > 0)
                {
                    url = fullurl;
                }
                else
                {
                    url = "https://pliamprojects.000webhostapp.com/rover";
                }
            }
            else
            {
                url = "https://pliamprojects.000webhostapp.com/rover";
            }

            //check url
            var client = new HttpClient();
            try
            {
                HttpResponseMessage response = await client.GetAsync(new Uri(url + "/habitable_mission.php?mission_id=1"));
                if (response.IsSuccessStatusCode)
                {
                    //success
                    var responseString = await response.Content.ReadAsStringAsync();
                }
                else
                {
                    //fail
                    url = "";
                }
            }
            catch (HttpRequestException e)
            {
                //fail
                url = "";
            }
            finally
            {
                client.Dispose();
            }

            return url;
        }

        public string GetUrl()
        {
            Task<string> get_url = Task.Run(async () => await GetUrlAsync());
            get_url.Wait();
            string url = get_url.Result;
            return url;
        }
        public async Task LoadPlanetInfo()
        {
            PositionPopup();
            InfoPopup.IsOpen = true;
            InfoPopup.Visibility = Visibility.Visible;

            //clear previous data
            PlanetNameText.Text = "Planet Name";
            TypeText.Text = "Planet Type";
            MassText.Text = "Planet Mass";
            RadiusText.Text = "Planet Radius";
            FluxText.Text = "Planet Flux";
            TeqText.Text = "Planet Teq";
            PeriodText.Text = "Planet Period";
            DistanceText.Text = "Planet Distance";
            ESIText.Text = "Planet ESI";
            PlanetHabitabilityText.Text = "Habitability";
            BitmapImage null_habitability = new BitmapImage(new Uri("ms-appx:///Assets/null_habitability_img.png"));
            PlanetHabitabilityImg.Source = null_habitability;
            planet_image = new BitmapImage(new Uri("ms-appx:///Assets/null_planet_img.png"));
            PlanetImageBrush.ImageSource = planet_image;

            //get data from source
            var client = new HttpClient();
            HttpResponseMessage response = await client.GetAsync(new Uri(GetUrl()+"/v_planet_mission_id_json.php?mission_id=" + GlobalVars.mission_id.ToString()));
            var jsonString = await response.Content.ReadAsStringAsync();
            JsonArray root = JsonValue.Parse(jsonString).GetArray();
            if (root.Count > 0)
            {
                planet_name = root.GetObjectAt(0).GetNamedString("Name");
                planet_type = root.GetObjectAt(0).GetNamedString("Type");
                planet_mass = root.GetObjectAt(0).GetNamedString("Mass (ME)");
                planet_radius = root.GetObjectAt(0).GetNamedString("Radius (RE)");
                planet_flux = root.GetObjectAt(0).GetNamedString("Flux (SE)");
                planet_teq = root.GetObjectAt(0).GetNamedString("Teq (K)");
                planet_period = root.GetObjectAt(0).GetNamedString("Period (days)");
                planet_distance = root.GetObjectAt(0).GetNamedString("Distance(ly)");
                planet_esi = root.GetObjectAt(0).GetNamedString("ESI");
                planet_id = root.GetObjectAt(0).GetNamedString("planet_id");

                PlanetNameText.Text = planet_name;
                TypeText.Text = planet_type;
                MassText.Text = planet_mass;
                RadiusText.Text = planet_radius;
                FluxText.Text = planet_flux;
                TeqText.Text = planet_teq;
                PeriodText.Text = planet_period;
                DistanceText.Text = planet_distance;
                ESIText.Text = planet_esi;

                client = new HttpClient();
                response = await client.GetAsync(new Uri(GetUrl()+"/habitable_mission.php?mission_id=" + GlobalVars.mission_id.ToString()));
                string habitability_string = await response.Content.ReadAsStringAsync();
                if (habitability_string == "1")
                {
                    //planet is habitable
                    PlanetHabitabilityText.Text = "Habitable";
                    BitmapImage habitable = new BitmapImage(new Uri("ms-appx:///Assets/planet_habitable.png"));
                    PlanetHabitabilityImg.Source = habitable;
                }
                else
                {
                    //planet is inhabitable
                    PlanetHabitabilityText.Text = "Inhabitable";
                    BitmapImage inhabitable = new BitmapImage(new Uri("ms-appx:///Assets/planet_inhabitable.png"));
                    PlanetHabitabilityImg.Source = inhabitable;
                }

                planet_image = new BitmapImage(new Uri("ms-appx:///Assets/PlanetImages/planet_" + planet_id + ".png"));
                PlanetImageBrush.ImageSource = planet_image;

                var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
                planet_favorite = localSettings.Values[planet_name];
                if (planet_favorite != null)
                {
                    //planet favorite has value
                    FavoriteButton.IsChecked = planet_favorite as bool?;
                }
                else
                {
                    //planet favorite is null
                    FavoriteButton.IsChecked = false;
                }

            }


            InfoPopup.IsOpen = false;
            InfoPopup.Visibility = Visibility.Collapsed;
        }

        private void InfoGrid_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            PositionPopup();
        }

        private async void InfoRefresh_Click(object sender, RoutedEventArgs e)
        {
            await LoadPlanetInfo();
        }

        private async void TypeButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/List_of_planet_types"));
        }

        private async void RadiusButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/Radius"));
        }

        private async void TeqButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/Planetary_equilibrium_temperature"));
        }

        private async void PeriodButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/Orbital_period"));
        }

        private async void FluxButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/Flux"));
        }

        private async void MassButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/Mass"));
        }

        private async void DistanceButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/Distance"));
        }

        private async void ESIButton_Click(object sender, RoutedEventArgs e)
        {
            await Launcher.LaunchUriAsync(new Uri(@"https://en.wikipedia.org/wiki/Earth_Similarity_Index"));
        }

        private void FavoriteButton_Checked(object sender, RoutedEventArgs e)
        {
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            localSettings.Values[planet_name] = true;
        }

        private void FavoriteButton_Unchecked(object sender, RoutedEventArgs e)
        {
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            localSettings.Values[planet_name] = false;
        }
    }
}
